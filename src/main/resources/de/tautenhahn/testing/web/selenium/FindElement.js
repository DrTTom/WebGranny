function describedAs(e, text){
   return matches(e.innerText, text) || _l(e, text) || matches(e.getAttribute('title'), text)
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
function sameLineAs(e, top, bottom) {r=_desc_(e); return top<=r.bottom && bottom>=r.top}
function before(e, top, bottom, left, right) {r=_desc_(e); return top>r.bottom || bottom>=r.top && left>r.right}
function after(e, top, bottom, left, right) {r=_desc_(e); return bottom<r.top || top top<=r.bottom && right<r.left}

