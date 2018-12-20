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
      foreignKey:'sender',
      onDelete: 'CASCADE' , 
      allowNull: false


  });

  message.belongsTo(models.user, {
      foreignKey:'receiver',
      onDelete: 'CASCADE' , 
      allowNull: false


  });

  message.belongsTo(models.conversation, {
      foreignKey:'convers', 
      onDelete: 'CASCADE' , 
      allowNull: false

  });

  };
  return message;
};