import React, { useCallback, useState } from "react";
import initAuthContext from "./initAuthContext";
import PropTypes from 'prop-types';
import { decodeJWT } from "../../utils/security/decodeJWT";

export const AuthContext = React.createContext();

export const AuthContextProvider = ({ children }) => {

  const [userData, setUserData] = useState(() => {
    const storedUserData = localStorage.getItem('token');
    return storedUserData ? {user: decodeJWT(storedUserData), isAuthenticated: true} : initAuthContext;
  });

  const updateUserData = useCallback((newData) => {
    setUserData((prevValue) => ({ ...prevValue, ...newData}))
  }, [])

  return (
    <AuthContext.Provider value={{userData, updateUserData}}>
      {children}
    </AuthContext.Provider>
  );
}

AuthContextProvider.propTypes = {
  children: PropTypes.node,
}


