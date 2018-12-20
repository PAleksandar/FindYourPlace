module.exports = (sequelize, DataTypes) => {
    const message = sequelize.define('message', {
      //attributes
      text: DataTypes.STRING,
      time: DataTypes.DATE,
    }, {
      timestamps: false
    });
    message.associate = function(models) {
      // associations can be defined here
      message.belongsTo(models.user, {
        foreignKey:'sender'

    });

    message.belongsTo(models.user, {
        foreignKey:'receiver'

    });

    message.belongsTo(models.conversation, {
        foreignKey:'convers'

    });

    };
    return message;
  };