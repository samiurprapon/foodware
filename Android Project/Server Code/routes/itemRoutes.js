const router = require('express').Router();

const itemController = require('../controllers/itemControllers');
const authMiddleware = require('../controllers/authMiddleware');

// vendor access
router.post('/create', authMiddleware.validation, itemController.create);
router.post('/update', authMiddleware.validation, itemController.update);

module.exports = {
    router
};