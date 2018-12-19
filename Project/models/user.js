module.exports = (sequelize, DataTypes) => {
  const user = sequelize.define('user', {
    //attributes
    email: DataTypes.STRING,
    firstName: DataTypes.STRING,
    lastName: DataTypes.STRING,
    isActive: DataTypes.BOOLEAN,
    profileImage: DataTypes.BLOB,
    birthday: DataTypes.DATE,
    password: DataTypes.STRING
  }, {
    timestamps: false
  });
  user.associate = function(models) {
    // associations can be defined here
  };
  return user;
};

