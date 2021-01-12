const router = require('express').Router();

const orderController = require('../controllers/orderControllers');
const authMiddleware = require('../controllers/authMiddleware');

// vendor access
router.post('/create', authMiddleware.validation, orderController.create);
router.post('/update', authMiddleware.validation, orderController.update);

module.exports = {
    router
};