function check() {
return {
  ids:[... document.querySelectorAll('*')].map(e => e.id).filter(id => id),
  labels:[... document.querySelectorAll('*')].map(e => {return { label: e.innerText, for: e.getAttribute('for')}}).filter(v => v.for),
  contentType: document.querySelector('meta[http-equiv="Content-Type"]') !== null,
  wrongImgs:[... document.querySelectorAll('img')].filter(v => !v.getAttribute('alt')).map(e => e.getAttribute('src'))
  }
}
return check()