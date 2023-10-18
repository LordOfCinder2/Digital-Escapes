import React, { useCallback, useState } from "react";
import PropTypes from 'prop-types';
import initAdminContext from "./initAdminContext";

export const AdminContext = React.createContext();

export const AdminContextProvider = ({ children }) => {

  const [adminData, setAdminData] = useState(initAdminContext);

  const updateAdminData = useCallback((newData) => {
    setAdminData((prevValue) => ({ ...prevValue, ...newData}))
  }, [])

  return (
    <AdminContext.Provider value={{adminData, updateAdminData}}>
      {children}
    </AdminContext.Provider>
  );
}

AdminContextProvider.propTypes = {
  children: PropTypes.node,
}
