module.exports = (sequelize, DataTypes) => {
  const comment = sequelize.define('comment', {
    //attributes
    text: DataTypes.STRING,
    date: DataTypes.DATE,
    like: DataTypes.INTEGER
  }, {
    timestamps: false
  });
  comment.associate = function(models) {
    // associations can be defined here
    comment.belongsTo(models.user, {
      foreignKey:'userId',
        onDelete: 'CASCADE' , 
        allowNull: false
  
  

    });
    comment.belongsTo(models.place, {
      foreignKey:'placeId',
        onDelete: 'CASCADE' , 
        allowNull: false
    });
  };
  return comment;
};

