<template>
  <div class="shopping-container">
    <div class="page-title">智能采购清单</div>
    
    <el-card class="form-card">
      <el-form :inline="true">
        <el-form-item label="选择食谱">
          <el-select v-model="selectedRecipes" multiple placeholder="请选择食谱" style="width: 300px">
            <el-option
              v-for="recipe in recipeList"
              :key="recipe.id"
              :label="recipe.caipumingcheng"
              :value="recipe.id">
            </el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="补充阈值">
          <el-input-number v-model="threshold" :min="0" :max="1" :step="0.1" style="width: 120px"></el-input-number>
          <span style="margin-left: 10px; color: #999;">（低于此比例时补充）</span>
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="generateList">生成采购清单</el-button>
          <el-button @click="exportList" :disabled="!shoppingList">导出清单</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card v-if="shoppingList" class="result-card">
      <div slot="header" class="clearfix">
        <span>采购清单</span>
        <span style="float: right; color: #999;">
          共 {{shoppingList.totalItems}} 项 | 必买 {{shoppingList.mustBuyItems}} 项 | 可选 {{shoppingList.optionalItems}} 项
        </span>
      </div>

      <div v-if="shoppingList.targetRecipes && shoppingList.targetRecipes.length > 0" class="target-recipes">
        <strong>目标食谱：</strong>
        <el-tag v-for="(recipe, index) in shoppingList.targetRecipes" :key="index" style="margin-right: 10px">
          {{recipe}}
        </el-tag>
      </div>

      <el-tabs v-model="activeCategory">
        <el-tab-pane 
          v-for="(items, category) in shoppingList.itemsByCategory" 
          :key="category" 
          :label="category + ' (' + items.length + ')'" 
          :name="category">
          
          <el-table :data="items" style="width: 100%">
            <el-table-column prop="shicaiName" label="食材名称" width="150"></el-table-column>
            <el-table-column prop="suggestedQuantity" label="建议采购量" width="120">
              <template slot-scope="scope">
                {{scope.row.suggestedQuantity}} {{scope.row.unit}}
              </template>
            </el-table-column>
            <el-table-column prop="currentStock" label="当前库存" width="120">
              <template slot-scope="scope">
                {{scope.row.currentStock}} {{scope.row.unit}}
              </template>
            </el-table-column>
            <el-table-column prop="gapQuantity" label="缺口量" width="120">
              <template slot-scope="scope">
                {{scope.row.gapQuantity}} {{scope.row.unit}}
              </template>
            </el-table-column>
            <el-table-column prop="mustBuy" label="类型" width="80">
              <template slot-scope="scope">
                <el-tag :type="scope.row.mustBuy ? 'danger' : 'info'" size="small">
                  {{scope.row.mustBuy ? '必买' : '可选'}}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="priority" label="优先级" width="80">
              <template slot-scope="scope">
                <el-tag :type="getPriorityType(scope.row.priority)" size="small">
                  {{getPriorityText(scope.row.priority)}}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="shelfLifeTip" label="保质期提示" min-width="200"></el-table-column>
            <el-table-column prop="usedInRecipes" label="用于食谱" min-width="200"></el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-card>

    <el-empty v-if="!shoppingList && !loading" description="请选择食谱并生成采购清单"></el-empty>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userId: localStorage.getItem('userid') || 11,
      recipeList: [],
      selectedRecipes: [],
      threshold: 0.3,
      shoppingList: null,
      activeCategory: '',
      loading: false
    }
  },
  created() {
    this.loadRecipes();
  },
  methods: {
    loadRecipes() {
      this.$http.get('caipuxinxi/list', {
        params: {
          page: 1,
          limit: 100
        }
      }).then(res => {
        if (res.data.code === 0) {
          this.recipeList = res.data.data.list;
        }
      });
    },
    
    generateList() {
      if (this.selectedRecipes.length === 0) {
        this.$message.warning('请至少选择一个食谱');
        return;
      }
      
      this.loading = true;
      const recipeIds = this.selectedRecipes.join(',');
      
      this.$http.get('shopping/suggest', {
        params: {
          userId: this.userId,
          recipeIds: recipeIds,
          threshold: this.threshold,
          includeSafetyStock: true,
          safetyStockFactor: 1.2
        }
      }).then(res => {
        this.loading = false;
        if (res.data.code === 0) {
          this.shoppingList = res.data.data;
          if (this.shoppingList.itemsByCategory) {
            const categories = Object.keys(this.shoppingList.itemsByCategory);
            if (categories.length > 0) {
              this.activeCategory = categories[0];
            }
          }
          this.$message.success('采购清单生成成功');
        } else {
          this.$message.error(res.data.msg || '生成失败');
        }
      }).catch(err => {
        this.loading = false;
        this.$message.error('生成失败：' + err.message);
      });
    },
    
    exportList() {
      window.open(`${this.$config.baseUrl}shopping/export?userId=${this.userId}&format=json`, '_blank');
    },
    
    getPriorityType(priority) {
      const types = {1: 'danger', 2: 'warning', 3: 'success'};
      return types[priority] || 'info';
    },
    
    getPriorityText(priority) {
      const texts = {1: '高', 2: '中', 3: '低'};
      return texts[priority] || '未知';
    }
  }
}
</script>

<style scoped>
.shopping-container {
  padding: 20px;
  max-width: 1400px;
  margin: 0 auto;
}

.page-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
  color: #333;
}

.form-card {
  margin-bottom: 20px;
}

.result-card {
  margin-top: 20px;
}

.target-recipes {
  margin-bottom: 20px;
  padding: 10px;
  background: #f5f7fa;
  border-radius: 4px;
}
</style>
