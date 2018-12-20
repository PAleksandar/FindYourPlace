module.exports = (sequelize, DataTypes) => {
    const event = sequelize.define('event', {
      //attributes
      name: DataTypes.STRING,
      longitude:DataTypes.FLOAT,
      latitude:DataTypes.FLOAT,
      image:DataTypes.BLOB,
      description:DataTypes.STRING,
      like:DataTypes.INTEGER

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
    })

    };
    return event;
  };