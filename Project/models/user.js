module.exports = (sequelize, DataTypes) => {
  const user = sequelize.define('user', {
    //attributes
    email: DataTypes.STRING,
    password: DataTypes.STRING,
    firstName: DataTypes.STRING,
    lastName: DataTypes.STRING,
    isActive: DataTypes.BOOLEAN,
    profileImage: DataTypes.BLOB,
    birthday: DataTypes.DATE
  }, {
    timestamps: false
  });
  user.associate = function(models) {
    // associations can be defined here
    user.belongsToMany(models.event, {
      through: 'User-Event',
      foreignKey: 'userId'
    });
  };
  return user;
};

