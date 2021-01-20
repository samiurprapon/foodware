const router = require('express').Router();

const restaurantController = require('../controllers/restaurantControllers');
const authMiddleware = require('../controllers/authMiddleware');

// vendor access
router.post('/create', authMiddleware.validation, restaurantController.create);
router.post('/update', authMiddleware.validation, restaurantController.update);

router.post('/validate', authMiddleware.validation, restaurantController.isCompleted);

router.post('/logo', authMiddleware.validation, restaurantController.logoChange);
router.post('/status', authMiddleware.validation, restaurantController.changeStatus);


module.exports = {
    router
};