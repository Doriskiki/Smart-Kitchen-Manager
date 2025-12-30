module.exports = {
  lintOnSave: false,
  publicPath: process.env.NODE_ENV === 'development' ? './' : '././',
  outputDir: 'dist',
  devServer: {
    port: 8080,
    proxy: {
      '/springbootct3p7': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        ws: true
      }
    }
  }
}