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
      foreignKey:'userId'
    });
    comment.belongsToMany(models.place, {
      through: 'Place-Comment',
      foreignKey: 'commentId',
      timestamps: false
    });
  };
  return comment;
};

