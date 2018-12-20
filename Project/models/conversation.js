module.exports = (sequelize, DataTypes) => {
    const conversation = sequelize.define('conversation', {
      //attributes
      
    }, {
      timestamps: false
    });
    conversation.associate = function(models) {
      // associations can be defined here
     
      conversation.belongsTo(models.user, {
        foreignKey:'user1'

    });

    conversation.belongsTo(models.user, {
        foreignKey:'user2'

    });
    
    };
    return conversation;
  };