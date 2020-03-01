module.exports = {
	publicPath: './',
	outputDir: '../src/main/resources/templates',
	devServer: {
		disableHostCheck: false,
		open: true,
		proxy: {
			'/api': {
				target: 'http://localhost:9000',
				https: false
			}
		}
	}
}
