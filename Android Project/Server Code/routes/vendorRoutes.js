const router = require('express').Router();

const vendorController = require('../controllers/vendorControllers');
const authMiddleware = require('../controllers/authMiddleware');

// vendor access
router.post('/profile', authMiddleware.validation, vendorController.read);
router.post('/create', authMiddleware.validation, vendorController.create);
router.post('/update', authMiddleware.validation, vendorController.update);

module.exports = {
    router
};