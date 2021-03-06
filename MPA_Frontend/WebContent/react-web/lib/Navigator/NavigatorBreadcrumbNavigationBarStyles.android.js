








'use strict';

var _ReactNavigatorNavigationBarStylesAndroid=require('./NavigatorNavigationBarStylesAndroid');var _ReactNavigatorNavigationBarStylesAndroid2=_interopRequireDefault(_ReactNavigatorNavigationBarStylesAndroid);
var _buildStyleInterpolator=require('./polyfills/buildStyleInterpolator');var _buildStyleInterpolator2=_interopRequireDefault(_buildStyleInterpolator);
var _merge=require('./polyfills/merge');var _merge2=_interopRequireDefault(_merge);function _interopRequireDefault(obj){return obj&&obj.__esModule?obj:{default:obj};}

var NAV_BAR_HEIGHT=_ReactNavigatorNavigationBarStylesAndroid2.default.General.NavBarHeight;

var SPACING=8;
var ICON_WIDTH=40;
var SEPARATOR_WIDTH=9;
var CRUMB_WIDTH=ICON_WIDTH+SEPARATOR_WIDTH;
var NAV_ELEMENT_HEIGHT=NAV_BAR_HEIGHT;

var OPACITY_RATIO=100;
var ICON_INACTIVE_OPACITY=0.6;
var MAX_BREADCRUMBS=10;

var CRUMB_BASE={
position:'absolute',
flexDirection:'row',
top:0,
width:CRUMB_WIDTH,
height:NAV_ELEMENT_HEIGHT,
backgroundColor:'transparent'};


var ICON_BASE={
width:ICON_WIDTH,
height:NAV_ELEMENT_HEIGHT};


var SEPARATOR_BASE={
width:SEPARATOR_WIDTH,
height:NAV_ELEMENT_HEIGHT};


var TITLE_BASE={
position:'absolute',
top:0,
height:NAV_ELEMENT_HEIGHT,
backgroundColor:'transparent',
alignItems:'flex-start'};


var FIRST_TITLE_BASE=(0,_merge2.default)(TITLE_BASE,{
left:0,
right:0});


var RIGHT_BUTTON_BASE={
position:'absolute',
top:0,
right:0,
overflow:'hidden',
opacity:1,
height:NAV_ELEMENT_HEIGHT,
backgroundColor:'transparent'};






var LEFT=[];
var CENTER=[];
var RIGHT=[];
for(var i=0;i<MAX_BREADCRUMBS;i++){
var crumbLeft=CRUMB_WIDTH*i+SPACING;
LEFT[i]={
Crumb:(0,_merge2.default)(CRUMB_BASE,{left:crumbLeft}),
Icon:(0,_merge2.default)(ICON_BASE,{opacity:ICON_INACTIVE_OPACITY}),
Separator:(0,_merge2.default)(SEPARATOR_BASE,{opacity:1}),
Title:(0,_merge2.default)(TITLE_BASE,{left:crumbLeft,opacity:0}),
RightItem:(0,_merge2.default)(RIGHT_BUTTON_BASE,{opacity:0})};

CENTER[i]={
Crumb:(0,_merge2.default)(CRUMB_BASE,{left:crumbLeft}),
Icon:(0,_merge2.default)(ICON_BASE,{opacity:1}),
Separator:(0,_merge2.default)(SEPARATOR_BASE,{opacity:0}),
Title:(0,_merge2.default)(TITLE_BASE,{
left:crumbLeft+ICON_WIDTH,
opacity:1}),

RightItem:(0,_merge2.default)(RIGHT_BUTTON_BASE,{opacity:1})};

var crumbRight=crumbLeft+50;
RIGHT[i]={
Crumb:(0,_merge2.default)(CRUMB_BASE,{left:crumbRight}),
Icon:(0,_merge2.default)(ICON_BASE,{opacity:0}),
Separator:(0,_merge2.default)(SEPARATOR_BASE,{opacity:0}),
Title:(0,_merge2.default)(TITLE_BASE,{
left:crumbRight+ICON_WIDTH,
opacity:0}),

RightItem:(0,_merge2.default)(RIGHT_BUTTON_BASE,{opacity:0})};

}


CENTER[0]={
Crumb:(0,_merge2.default)(CRUMB_BASE,{left:SPACING+CRUMB_WIDTH}),
Icon:(0,_merge2.default)(ICON_BASE,{opacity:0}),
Separator:(0,_merge2.default)(SEPARATOR_BASE,{opacity:0}),
Title:(0,_merge2.default)(FIRST_TITLE_BASE,{opacity:1}),
RightItem:CENTER[0].RightItem};

LEFT[0].Title=(0,_merge2.default)(FIRST_TITLE_BASE,{opacity:0});
RIGHT[0].Title=(0,_merge2.default)(FIRST_TITLE_BASE,{opacity:0});


var buildIndexSceneInterpolator=function buildIndexSceneInterpolator(startStyles,endStyles){
return{
Crumb:(0,_buildStyleInterpolator2.default)({
left:{
type:'linear',
from:startStyles.Crumb.left,
to:endStyles.Crumb.left,
min:0,
max:1,
extrapolate:true}}),


Icon:(0,_buildStyleInterpolator2.default)({
opacity:{
type:'linear',
from:startStyles.Icon.opacity,
to:endStyles.Icon.opacity,
min:0,
max:1}}),


Separator:(0,_buildStyleInterpolator2.default)({
opacity:{
type:'linear',
from:startStyles.Separator.opacity,
to:endStyles.Separator.opacity,
min:0,
max:1}}),


Title:(0,_buildStyleInterpolator2.default)({
opacity:{
type:'linear',
from:startStyles.Title.opacity,
to:endStyles.Title.opacity,
min:0,
max:1},

left:{
type:'linear',
from:startStyles.Title.left,
to:endStyles.Title.left,
min:0,
max:1,
extrapolate:true}}),


RightItem:(0,_buildStyleInterpolator2.default)({
opacity:{
type:'linear',
from:startStyles.RightItem.opacity,
to:endStyles.RightItem.opacity,
min:0,
max:1,
round:OPACITY_RATIO}})};



};

var Interpolators=CENTER.map(function(_,ii){
return{

RightToCenter:buildIndexSceneInterpolator(RIGHT[ii],CENTER[ii]),

CenterToLeft:buildIndexSceneInterpolator(CENTER[ii],LEFT[ii]),

RightToLeft:buildIndexSceneInterpolator(RIGHT[ii],LEFT[ii])};

});





module.exports={
Interpolators:Interpolators,
Left:LEFT,
Center:CENTER,
Right:RIGHT,
IconWidth:ICON_WIDTH,
IconHeight:NAV_BAR_HEIGHT,
SeparatorWidth:SEPARATOR_WIDTH,
SeparatorHeight:NAV_BAR_HEIGHT};