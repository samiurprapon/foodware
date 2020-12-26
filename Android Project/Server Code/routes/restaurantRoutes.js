const router = require('express').Router();

const retaurantController = require('../controllers/restaurantControllers');
const authMiddleware = require('../controllers/authMiddleware');

// vendor access
router.post('/create', authMiddleware.validation, retaurantController.create);
router.post('/update', authMiddleware.validation, retaurantController.update);

module.exports = {
    router
};