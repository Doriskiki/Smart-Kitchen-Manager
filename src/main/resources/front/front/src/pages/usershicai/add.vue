<template>
<div>
	<div :style='{"padding":"12px","margin":"10px auto","borderColor":"#dbd9f4","borderRadius":"8px","background":"#fff","borderWidth":"0 0 2px","width":"1200px","borderStyle":"solid"}' class="breadcrumb-preview">
		<el-breadcrumb :separator="'Ξ'" :style='{"width":"100%","margin":"0 auto","fontSize":"14px","lineHeight":"1","display":"flex"}'>
			<el-breadcrumb-item>首页</el-breadcrumb-item>
			<el-breadcrumb-item>我的食材库</el-breadcrumb-item>
			<el-breadcrumb-item>{{id ? '编辑' : '添加'}}食材</el-breadcrumb-item>
		</el-breadcrumb>
	</div>
	
	<div class="add-preview" :style='{"width":"1200px","margin":"10px auto","position":"relative","background":"none"}'>
		<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px" class="demo-ruleForm" :style='{"border":"2px solid #dbd9f4","padding":"40px 20px","borderRadius":"8px","background":"#fff"}'>
			
			<el-form-item label="食材名称" prop="shicaiName">
				<el-input v-model="ruleForm.shicaiName" placeholder="请输入食材名称" clearable></el-input>
			</el-form-item>
			
			<el-form-item label="数量" prop="quantity">
				<el-input-number v-model="ruleForm.quantity" :min="1" :max="99999" label="数量"></el-input-number>
			</el-form-item>
			
			<el-form-item label="单位" prop="unit">
				<el-select v-model="ruleForm.unit" placeholder="请选择单位" clearable>
					<el-option label="克" value="克"></el-option>
					<el-option label="千克" value="千克"></el-option>
					<el-option label="毫升" value="毫升"></el-option>
					<el-option label="升" value="升"></el-option>
					<el-option label="个" value="个"></el-option>
					<el-option label="根" value="根"></el-option>
					<el-option label="斤" value="斤"></el-option>
					<el-option label="袋" value="袋"></el-option>
					<el-option label="盒" value="盒"></el-option>
				</el-select>
			</el-form-item>
			
			<el-form-item label="购买日期" prop="purchaseDate">
				<el-date-picker
					v-model="ruleForm.purchaseDate"
					type="datetime"
					placeholder="选择购买日期"
					value-format="yyyy-MM-dd HH:mm:ss"
					:style='{"width":"100%"}'>
				</el-date-picker>
			</el-form-item>
			
			<el-form-item label="过期日期" prop="expiryDate">
				<el-date-picker
					v-model="ruleForm.expiryDate"
					type="datetime"
					placeholder="选择过期日期"
					value-format="yyyy-MM-dd HH:mm:ss"
					:style='{"width":"100%"}'>
				</el-date-picker>
			</el-form-item>
			
			<el-form-item label="状态" prop="status">
				<el-radio-group v-model="ruleForm.status">
					<el-radio label="new">新鲜</el-radio>
					<el-radio label="used">已使用</el-radio>
					<el-radio label="expired">已过期</el-radio>
					<el-radio label="discarded">已丢弃</el-radio>
				</el-radio-group>
			</el-form-item>
			
			<el-form-item>
				<el-button type="primary" @click="submitForm('ruleForm')" :style='{"cursor":"pointer","border":"0","padding":"0px 30px","margin":"0 10px 0 0","outline":"none","color":"#666","borderRadius":"6px","background":"linear-gradient(90deg, rgba(255,233,100,1) 0%, rgba(194,248,126,1) 29%, rgba(181,233,252,1) 61%, rgba(246,172,218,1) 100%)","width":"auto","fontSize":"14px","lineHeight":"42px","height":"42px"}'>提交</el-button>
				<el-button @click="resetForm('ruleForm')" :style='{"cursor":"pointer","border":"0","padding":"0px 30px","margin":"0 10px 0 0","outline":"none","color":"#666","borderRadius":"6px","background":"#f0f0f0","width":"auto","fontSize":"14px","lineHeight":"42px","height":"42px"}'>重置</el-button>
				<el-button @click="back()" :style='{"cursor":"pointer","border":"0","padding":"0px 30px","margin":"0 10px 0 0","outline":"none","color":"#666","borderRadius":"6px","background":"#f0f0f0","width":"auto","fontSize":"14px","lineHeight":"42px","height":"42px"}'>返回</el-button>
			</el-form-item>
		</el-form>
	</div>
</div>
</template>

<script>
export default {
	data() {
		return {
			id: '',
			ruleForm: {
				shicaiName: '',
				quantity: 1,
				unit: '克',
				purchaseDate: '',
				expiryDate: '',
				status: 'new'
			},
			rules: {
				shicaiName: [
					{ required: true, message: '请输入食材名称', trigger: 'blur' }
				],
				quantity: [
					{ required: true, message: '请输入数量', trigger: 'blur' }
				],
				unit: [
					{ required: true, message: '请选择单位', trigger: 'change' }
				],
				purchaseDate: [
					{ required: true, message: '请选择购买日期', trigger: 'change' }
				],
				expiryDate: [
					{ required: true, message: '请选择过期日期', trigger: 'change' }
				],
				status: [
					{ required: true, message: '请选择状态', trigger: 'change' }
				]
			}
		}
	},
	created() {
		this.id = this.$route.query.id;
		if (this.id) {
			this.getInfo();
		} else {
			// 默认设置购买日期为当前时间
			this.ruleForm.purchaseDate = this.formatDateTime(new Date());
		}
	},
	methods: {
		// 获取详情
		getInfo() {
			this.$http.get(`usershicai/info/${this.id}`).then(res => {
				if (res.data && res.data.code === 0) {
					this.ruleForm = res.data.data;
				}
			});
		},
		// 提交表单
		submitForm(formName) {
			this.$refs[formName].validate((valid) => {
				if (valid) {
					let userid = this.$storage.get('userid');
					this.ruleForm.userid = userid;
					
					let url = this.id ? 'usershicai/update' : 'usershicai/save';
					this.$http.post(url, this.ruleForm).then(res => {
						if (res.data && res.data.code === 0) {
							this.$message.success(this.id ? '修改成功' : '添加成功');
							this.$router.push('/index/usershicaiList');
						} else {
							this.$message.error(res.data.msg);
						}
					});
				} else {
					return false;
				}
			});
		},
		// 重置表单
		resetForm(formName) {
			this.$refs[formName].resetFields();
		},
		// 返回
		back() {
			this.$router.go(-1);
		},
		// 格式化日期时间
		formatDateTime(date) {
			let d = new Date(date);
			return d.getFullYear() + '-' + 
				   String(d.getMonth() + 1).padStart(2, '0') + '-' + 
				   String(d.getDate()).padStart(2, '0') + ' ' +
				   String(d.getHours()).padStart(2, '0') + ':' +
				   String(d.getMinutes()).padStart(2, '0') + ':' +
				   String(d.getSeconds()).padStart(2, '0');
		}
	}
}
</script>

<style scoped>
.el-form {
	max-width: 800px;
	margin: 0 auto;
}
</style>
