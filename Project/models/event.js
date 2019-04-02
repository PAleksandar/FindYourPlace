module.exports = (sequelize, DataTypes) => {
    const event = sequelize.define('event', {
      //attributes
      name: DataTypes.STRING,
      tag:DataTypes.STRING,
      image:DataTypes.TEXT,
      description:DataTypes.STRING,
      like:DataTypes.INTEGER,
      date:DataTypes.DATE

    }, {
      timestamps: false
    });
    event.associate = function(models) {
      // associations can be defined here
      event.belongsTo(models.place, {
        foreignKey:'placeId'

    });
    event.belongsTo(models.user, {
        foreignKey:'ownerUserId'

    });
    event.belongsToMany(models.user, {
        through: 'User-Event',
        foreignKey: 'eventId'
    });

    };
    return event;
  };