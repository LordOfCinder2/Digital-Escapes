export const lastPath = (path) =>{
  if(!path) return;
  return path.split('/').pop();
}