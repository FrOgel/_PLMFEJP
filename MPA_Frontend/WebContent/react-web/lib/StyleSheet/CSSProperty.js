










'use strict';




var isUnitlessNumber={
animationIterationCount:true,
boxFlex:true,
boxFlexGroup:true,
boxOrdinalGroup:true,
columnCount:true,
flex:true,
flexGrow:true,
flexPositive:true,
flexShrink:true,
flexNegative:true,
flexOrder:true,
fontWeight:true,
lineClamp:true,
lineHeight:true,
opacity:true,
order:true,
orphans:true,
tabSize:true,
widows:true,
zIndex:true,
zoom:true,


fillOpacity:true,
stopOpacity:true,
strokeDashoffset:true,
strokeOpacity:true,
strokeWidth:true};








function prefixKey(prefix,key){
return prefix+key.charAt(0).toUpperCase()+key.substring(1);
}





var prefixes=['Webkit','ms','Moz','O'];



Object.keys(isUnitlessNumber).forEach(function(prop){
prefixes.forEach(function(prefix){
isUnitlessNumber[prefixKey(prefix,prop)]=isUnitlessNumber[prop];
});
});










var shorthandPropertyExpansions={
background:{
backgroundAttachment:true,
backgroundColor:true,
backgroundImage:true,
backgroundPositionX:true,
backgroundPositionY:true,
backgroundRepeat:true},

backgroundPosition:{
backgroundPositionX:true,
backgroundPositionY:true},

border:{
borderWidth:true,
borderStyle:true,
borderColor:true},

borderBottom:{
borderBottomWidth:true,
borderBottomStyle:true,
borderBottomColor:true},

borderLeft:{
borderLeftWidth:true,
borderLeftStyle:true,
borderLeftColor:true},

borderRight:{
borderRightWidth:true,
borderRightStyle:true,
borderRightColor:true},

borderTop:{
borderTopWidth:true,
borderTopStyle:true,
borderTopColor:true},

font:{
fontStyle:true,
fontVariant:true,
fontWeight:true,
fontSize:true,
lineHeight:true,
fontFamily:true},

outline:{
outlineWidth:true,
outlineStyle:true,
outlineColor:true}};



var CSSProperty={
isUnitlessNumber:isUnitlessNumber,
shorthandPropertyExpansions:shorthandPropertyExpansions};


module.exports=CSSProperty;