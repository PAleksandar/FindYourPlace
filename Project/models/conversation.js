module.exports = (sequelize, DataTypes) => {
  const conversation = sequelize.define('conversation', {
    //attributes
    
  }, {
    timestamps: false
  });
  conversation.associate = function(models) {
    // associations can be defined here
   
  conversation.belongsTo(models.user, {
      foreignKey:'user1',
      onDelete: 'CASCADE' , 
      allowNull: false

     
  });

  conversation.belongsTo(models.user, {
      foreignKey:'user2',
      onDelete: 'CASCADE' , 
      allowNull: false


  });
  
  };
  return conversation;
};