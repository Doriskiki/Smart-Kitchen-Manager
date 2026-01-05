# Git 上传项目完整指南

## 目标
将整个项目（包括图片、文档等所有文件）上传到 Git 仓库。

---

## 准备工作

### 1. 检查 .gitignore 文件

当前 `.gitignore` 已经配置好，会排除：
- ✅ `target/` - Maven 编译输出（不需要上传）
- ✅ `node_modules/` - npm 依赖（不需要上传）
- ✅ `.idea/` - IDEA 配置（不需要上传）
- ✅ `.vscode/` - VSCode 配置（不需要上传）

**重要**：图片文件夹 `upload/` 和 `src/main/resources/static/upload/` 不在排除列表中，会被上传 ✅

### 2. 确认要上传的重要文件

```
✅ 源代码：src/
✅ 配置文件：pom.xml, application.yml
✅ 数据库文件：db/*.sql
✅ 图片资源：src/main/resources/static/upload/
✅ 前端代码：src/main/resources/front/
✅ 文档：docs/, *.md
✅ 项目根目录的图片：upload/
```

---

## 完整上传步骤

### 步骤1：初始化 Git 仓库（如果还没有）

```bash
# 检查是否已经是 Git 仓库
git status

# 如果提示 "not a git repository"，则初始化
git init
```

### 步骤2：配置 Git 用户信息（首次使用）

```bash
# 配置用户名
git config --global user.name "你的名字"

# 配置邮箱
git config --global user.email "你的邮箱@example.com"

# 查看配置
git config --list
```

### 步骤3：添加所有文件到暂存区

```bash
# 添加所有文件（包括图片）
git add .

# 查看将要提交的文件
git status
```

### 步骤4：提交到本地仓库

```bash
# 提交所有更改
git commit -m "feat: 智能厨房管理系统完整版-前端优化"

# 或者更详细的提交信息
git commit -m "feat: 智能厨房管理系统完整版

- 实现基于库存的智能推荐
- 实现热门推荐功能
- 实现个性化推荐（协同过滤）
- 修复图片显示问题
- 修复热门推荐数据问题
- 包含完整的数据库文件和图片资源"
```

### 步骤5：创建远程仓库

在 GitHub/Gitee/GitLab 上创建新仓库：

**GitHub**：
1. 访问 https://github.com/new
2. 填写仓库名称：`Smart-Kitchen-Manager`
3. 选择 Public 或 Private
4. **不要**勾选 "Initialize with README"（因为本地已有）
5. 点击 "Create repository"

**Gitee**（国内推荐）：
1. 访问 https://gitee.com/projects/new
2. 填写仓库名称：`Smart-Kitchen-Manager`
3. 选择公开或私有
4. 点击 "创建"

### 步骤6：关联远程仓库

```bash
# GitHub
git remote add origin https://github.com/你的用户名/Smart-Kitchen-Manager.git

# 或 Gitee（国内推荐，速度快）
git remote add origin https://gitee.com/你的用户名/Smart-Kitchen-Manager.git

# 查看远程仓库
git remote -v
```

### 步骤7：推送到远程仓库

```bash
# 首次推送（设置上游分支）
git push -u origin master

# 或者如果默认分支是 main
git push -u origin main

# 如果推送失败，可能需要先拉取
git pull origin master --allow-unrelated-histories
git push -u origin master
```

---

## 完整命令汇总（复制粘贴版）

```bash
# 1. 检查 Git 状态
git status

# 2. 如果不是 Git 仓库，初始化
git init

# 3. 配置用户信息（首次使用）
git config --global user.name "你的名字"
git config --global user.email "你的邮箱@example.com"

# 4. 添加所有文件
git add .

# 5. 查看将要提交的文件
git status

# 6. 提交到本地仓库
git commit -m "feat: 智能厨房管理系统完整版 - 包含智能推荐功能"

# 7. 关联远程仓库（选择 GitHub 或 Gitee）
# GitHub:
git remote add origin https://github.com/你的用户名/Smart-Kitchen-Manager.git
# Gitee:
git remote add origin https://gitee.com/你的用户名/Smart-Kitchen-Manager.git

# 8. 推送到远程仓库
git push -u origin master
```

---

## 特殊情况处理

### 情况1：文件太大无法推送

如果图片文件太大（超过 100MB），Git 会拒绝推送。

**解决方案1：使用 Git LFS（大文件存储）**

```bash
# 安装 Git LFS
# Windows: 下载安装 https://git-lfs.github.com/
# Mac: brew install git-lfs
# Linux: sudo apt-get install git-lfs

# 初始化 Git LFS
git lfs install

# 追踪大文件（图片）
git lfs track "*.jpg"
git lfs track "*.png"
git lfs track "*.jpeg"

# 添加 .gitattributes
git add .gitattributes

# 重新提交
git add .
git commit -m "feat: 使用 Git LFS 管理大文件"
git push -u origin master
```

**解决方案2：压缩图片**

```bash
# 使用工具压缩图片
# 推荐工具：TinyPNG, ImageOptim
```

### 情况2：推送被拒绝（remote rejected）

```bash
# 错误信息：! [rejected] master -> master (fetch first)

# 解决方案：先拉取再推送
git pull origin master --allow-unrelated-histories
git push -u origin master
```

### 情况3：需要输入用户名密码

**GitHub（推荐使用 Token）**：

1. 生成 Personal Access Token：
   - 访问 https://github.com/settings/tokens
   - 点击 "Generate new token"
   - 勾选 `repo` 权限
   - 复制生成的 token

2. 使用 token 推送：
   ```bash
   # 用户名：你的 GitHub 用户名
   # 密码：粘贴刚才复制的 token
   git push -u origin master
   ```

**Gitee**：
- 直接使用 Gitee 账号密码即可

### 情况4：已经有远程仓库，需要更换

```bash
# 查看当前远程仓库
git remote -v

# 删除旧的远程仓库
git remote remove origin

# 添加新的远程仓库
git remote add origin https://github.com/新用户名/新仓库.git

# 推送
git push -u origin master
```

---

## 验证上传是否成功

### 1. 检查远程仓库

访问您的 GitHub/Gitee 仓库页面，确认：
- ✅ 所有源代码文件都在
- ✅ `upload/` 文件夹中的图片都在
- ✅ `src/main/resources/static/upload/` 中的图片都在
- ✅ `db/` 文件夹中的 SQL 文件都在
- ✅ 文档文件（.md）都在

### 2. 克隆测试

在另一个目录测试克隆：

```bash
# 克隆仓库
git clone https://github.com/你的用户名/Smart-Kitchen-Manager.git

# 进入目录
cd Smart-Kitchen-Manager

# 检查文件
ls -la
ls upload/
ls src/main/resources/static/upload/
```

---

## 后续更新流程

当您修改代码后，使用以下命令更新：

```bash
# 1. 查看修改的文件
git status

# 2. 添加修改的文件
git add .

# 3. 提交
git commit -m "fix: 修复某个问题"

# 4. 推送
git push
```

---

## 常用 Git 命令

```bash
# 查看状态
git status

# 查看提交历史
git log
git log --oneline

# 查看某个文件的修改
git diff 文件名

# 撤销修改（未提交）
git checkout -- 文件名

# 撤销提交（已提交但未推送）
git reset --soft HEAD~1

# 查看远程仓库
git remote -v

# 拉取最新代码
git pull

# 推送代码
git push

# 创建分支
git branch 分支名
git checkout 分支名

# 或者一步创建并切换
git checkout -b 分支名

# 合并分支
git checkout master
git merge 分支名

# 删除分支
git branch -d 分支名
```

---

## 提交信息规范

建议使用规范的提交信息格式：

```bash
# 新功能
git commit -m "feat: 添加智能推荐功能"

# 修复 bug
git commit -m "fix: 修复图片显示问题"

# 文档更新
git commit -m "docs: 更新 README 文档"

# 代码重构
git commit -m "refactor: 重构推荐算法"

# 性能优化
git commit -m "perf: 优化数据库查询性能"

# 测试
git commit -m "test: 添加单元测试"

# 构建/依赖
git commit -m "build: 更新 Maven 依赖"

# 样式修改
git commit -m "style: 格式化代码"
```

---

## 创建 README.md

在推送前，建议创建一个 README.md 文件：

```markdown
# 智能厨房管理系统

## 项目简介
基于 Spring Boot + Vue 的智能厨房管理系统，提供食材管理、菜谱推荐、智能推荐等功能。

## 主要功能
- 🥗 食材管理：添加、编辑、删除食材，自动过期提醒
- 📖 菜谱管理：浏览、搜索、收藏菜谱
- 🤖 智能推荐：
  - 基于库存推荐：根据现有食材推荐菜谱
  - 热门推荐：推荐最受欢迎的菜谱
  - 个性化推荐：基于协同过滤的个性化推荐
- 👤 用户管理：用户注册、登录、个人信息管理
- 📊 数据统计：食材使用统计、菜谱热度统计

## 技术栈
- 后端：Spring Boot 2.1.0, MyBatis Plus, Redis
- 前端：Vue 2.x, Element UI
- 数据库：MySQL 8.0
- 缓存：Redis

## 快速开始

### 1. 环境要求
- JDK 1.8+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+
- Node.js 14+

### 2. 数据库配置
```bash
# 导入数据库
mysql -u root -p < db/springbootct3p7.sql
```

### 3. 修改配置
编辑 `src/main/resources/application.yml`：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/springbootct3p7
    username: root
    password: 你的密码
```

### 4. 启动后端
```bash
mvn spring-boot:run
```

### 5. 启动前端
```bash
cd src/main/resources/front/front
npm install
npm run serve
```

### 6. 访问系统
- 前端：http://localhost:8080
- 后端：http://localhost:8081

## 默认账号
- 管理员：admin / admin
- 普通用户：user1 / 123456

## 项目结构
```
Smart-Kitchen-Manager/
├── src/
│   ├── main/
│   │   ├── java/com/
│   │   │   ├── controller/     # 控制器
│   │   │   ├── service/        # 服务层
│   │   │   ├── dao/            # 数据访问层
│   │   │   ├── entity/         # 实体类
│   │   │   └── utils/          # 工具类
│   │   └── resources/
│   │       ├── static/upload/  # 静态资源
│   │       ├── front/          # 前端代码
│   │       └── application.yml # 配置文件
│   └── test/                   # 测试代码
├── db/                         # 数据库文件
├── docs/                       # 文档
└── pom.xml                     # Maven 配置

## 开发文档
- [智能推荐算法详解](智能推荐算法详解.md)
- [系统核心功能说明](docs/系统核心功能实现说明.md)
- [数据库设计](db/springbootct3p7.sql)

## 许可证
MIT License

## 联系方式
- 作者：[你的名字]
- 邮箱：[你的邮箱]
```

---

## 最终检查清单

上传前请确认：

- [ ] `.gitignore` 文件配置正确
- [ ] 所有图片文件都在 `upload/` 和 `src/main/resources/static/upload/`
- [ ] 数据库 SQL 文件在 `db/` 目录
- [ ] `application.yml` 中的敏感信息已移除或使用占位符
- [ ] 创建了 `README.md` 文件
- [ ] 所有文档文件（.md）都已添加
- [ ] 测试克隆后项目可以正常运行

---

## 快速上传脚本

创建一个 `git_push.sh` 脚本（Linux/Mac）：

```bash
#!/bin/bash

echo "开始上传项目到 Git..."

# 添加所有文件
git add .

# 提交
echo "请输入提交信息："
read commit_message
git commit -m "$commit_message"

# 推送
git push

echo "上传完成！"
```

Windows 版本 `git_push.bat`：

```batch
@echo off
echo 开始上传项目到 Git...

git add .

set /p commit_message=请输入提交信息：
git commit -m "%commit_message%"

git push

echo 上传完成！
pause
```

使用方法：
```bash
# Linux/Mac
chmod +x git_push.sh
./git_push.sh

# Windows
git_push.bat
```

---

## 总结

完整的上传流程：
1. ✅ 初始化 Git 仓库
2. ✅ 配置用户信息
3. ✅ 添加所有文件（包括图片）
4. ✅ 提交到本地仓库
5. ✅ 创建远程仓库
6. ✅ 关联远程仓库
7. ✅ 推送到远程仓库
8. ✅ 验证上传成功

所有图片、文档、代码都会被上传，因为它们不在 `.gitignore` 排除列表中！
