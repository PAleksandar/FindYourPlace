module.exports = (sequelize, DataTypes) => {
  const user = sequelize.define('user', {
    //attributes
    email: DataTypes.STRING,
    password: DataTypes.STRING,
    firstName: DataTypes.STRING,
    lastName: DataTypes.STRING,
    isActive: DataTypes.BOOLEAN,
    profileImage: DataTypes.BLOB, //{
      //type: DataTypes.BLOB,
     // allowNull: false,
     /* get() {
        return this.getDataValue('profileImage');//.toString('utf8'); // or whatever encoding is right
      },
      set(val) {
        this.setDataValue('profileImage', val);
      }*/
   // },
    
   image: DataTypes.TEXT,
   
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

