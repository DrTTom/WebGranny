function describedAs(e, text){
   return matches(e.innerText, text) || _l(e, text)
}
function matches(v, text){return v===text||RegExp('^'+text+'$').test(v)}
function _list_(root, elem){return [... document.querySelector(root).querySelectorAll(elem)]}
function _l(e, text){return _list_('body','label').filter(l=>l.getAttribute('for')===e.id).some(e=>describedAs(e,text))}
function _desc_(e) {
   let rect = e.getBoundingClientRect();
   let hoff= document.body.scrollLeft;
   let voff= document.body.scrollTop;
   return {id: _id_(e), left: rect.left + hoff, right:rect.right+ hoff, bottom:rect.bottom+voff, top:rect.top+voff}
}
function _id_(e){if(!e.id){e.id ='wg'+Math.random().toString(36).substr(2, 9)} return e.id}

