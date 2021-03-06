





'use strict';

var _react=require('react');var _react2=_interopRequireDefault(_react);
var _reactDom=require('react-dom');var _reactDom2=_interopRequireDefault(_reactDom);
var _ReactGetLayout=require('./getLayout.web');var _ReactGetLayout2=_interopRequireDefault(_ReactGetLayout);function _interopRequireDefault(obj){return obj&&obj.__esModule?obj:{default:obj};}

var LayoutMixin={
getInitialState:function getInitialState(){
return{layout:{}};
},

componentDidMount:function componentDidMount(){
this.layoutHandle();
},

componentDidUpdate:function componentDidUpdate(){
this.layoutHandle();
},

layoutHandle:function layoutHandle(){
if(this.props.onLayout){

var layout=(0,_ReactGetLayout2.default)(_reactDom2.default.findDOMNode(this));
var stateLayout=this.state.layout;
if(stateLayout.x!==layout.x||stateLayout.y!==layout.y||stateLayout.width!==layout.width||stateLayout.height!==layout.height){
this.props.onLayout({nativeEvent:{layout:layout}});
this.setState({layout:layout});
}
}
}};


module.exports={
Mixin:LayoutMixin};