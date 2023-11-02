const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
    app.use(
        '/api',
        createProxyMiddleware({
            target: 'http://ok-archive:2023',
            changeOrigin: true,
})
);
    console.log("이거된다");
};