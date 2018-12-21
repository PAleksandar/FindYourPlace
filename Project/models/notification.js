module.exports = (sequelize, DataTypes) => {
  const notification = sequelize.define('notification', {
    //attributes
    text: DataTypes.STRING,
    date: DataTypes.DATE
  }, {
    timestamps: false
  });
  notification.associate = function(models) {
    // associations can be defined here
    notification.belongsTo(models.user, {
      foreignKey: 'userId'
    });
    notification.belongsTo(models.event, {
      foreignKey: 'eventId',
     
    });
  };
  return notification;
};

