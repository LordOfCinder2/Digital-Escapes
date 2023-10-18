const trimObjectValues = (obj) => {
  if(!obj) return;
  const objToTrimm = {...obj}
  const arrProps = Object.keys(objToTrimm);
  arrProps.forEach(k =>(
    objToTrimm[k] = typeof objToTrimm[k] === 'string' 
      ? objToTrimm[k].trim() 
      : objToTrimm[k]
  )); 
  return objToTrimm
}

export default trimObjectValues