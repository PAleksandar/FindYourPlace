module.exports = (sequelize, DataTypes) => {
    const place = sequelize.define('place', {
      //attributes
      name: DataTypes.STRING,
      tag:DataTypes.STRING,
      latitude:DataTypes.FLOAT,
      longitude:DataTypes.FLOAT,
      image:DataTypes.BLOB,
      description:DataTypes.STRING,
      like:DataTypes.INTEGER

    }, {
      timestamps: false
    });
    place.associate = function(models) {
      // associations can be defined here
      place.belongsToMany(models.comment, {
        through: 'Place-Comment',
        foreignKey: 'placeId'
      });

    };
    return place;
  };