const Sequelize = require('sequelize');

const sequelize = new Sequelize('FindYourPlace','postgres','postgres',{dialect:'postgres'});

const models = {
  user:sequelize.import('../models/user')
};

Object.keys(models).forEach(modelName => {
  if (models[modelName].associate) {
    models[modelName].associate(models);
  }
});

models.sequelize = sequelize;
models.Sequelize = Sequelize;

module.exports = models;