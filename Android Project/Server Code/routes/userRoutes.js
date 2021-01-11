const router = require('express').Router();

const userController = require('../controllers/userControllers');
const authMiddleware = require('../controllers/authMiddleware');

// vendor access
router.post('/create', authMiddleware.validation, userController.create);
router.post('/update', authMiddleware.validation, userController.update);

module.exports = {
    router
};