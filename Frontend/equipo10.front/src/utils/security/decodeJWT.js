import jwt_decode from 'jwt-decode';

export const decodeJWT = (token) =>{
  if(!token) return;
  const {name, role, sub, userId} = jwt_decode(token)
  const usrData = {
    name: name,
    role: role,
    sub: sub,
    id: userId 
  }
  return usrData
} 