const Sequelize = require('sequelize');

const sequelize = new Sequelize('FindYourPlace','postgres','postgres',{dialect:'postgres'});

const models = {
  user:sequelize.import('../models/user'),
  message:sequelize.import('../models/message'),
  conversation:sequelize.import('../models/conversation'),
  comment:sequelize.import('../models/comment'),
  notification:sequelize.import('../models/notification')
};

Object.keys(models).forEach(modelName => {
  if (models[modelName].associate) {
    models[modelName].associate(models);
  }
});

models.sequelize = sequelize;
models.Sequelize = Sequelize;

module.exports = models;