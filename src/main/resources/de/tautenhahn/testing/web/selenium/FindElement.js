function describedBy(e, text){return matches(e.innerText, text)
}
function matches(v, text){return v===text||RegExp('^'+text+'$').test(v)}
function traverse(root){return [... document.querySelector(root).querySelectorAll('*')]}
function _l(e, text){return document.querySelector(body).querySelectorAll('label').filter(l=>l.getAttribute('for')===e.id).hasAny()}