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
    notification.belongsToMany(models.event, {
      through: 'Event-Notification',
      foreignKey: 'notificationId',
      timestamps: false
    });
  };
  return notification;
};

