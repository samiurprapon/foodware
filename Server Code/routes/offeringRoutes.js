const router = require('express').Router();

const offeringController = require('../controllers/offeringControllers');
const authMiddleware = require('../controllers/authMiddleware');

// vendor access
router.post('/create', authMiddleware.validation, offeringController.create);
router.post('/update', authMiddleware.validation, offeringController.update);

module.exports = {
    router
};