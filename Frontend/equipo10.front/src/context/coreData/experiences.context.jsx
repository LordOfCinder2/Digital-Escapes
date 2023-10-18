import React, { useCallback, useReducer } from "react";
import initExperiencesContext from "./initExperiencesContext";
import PropTypes from 'prop-types';

export const ExperiencesContext = React.createContext(initExperiencesContext);

const experiencesReducer = (state, action) => {
  switch (action.type) {
    case 'UPDATE_EXPERIENCE_DATA':
      return { ...state, ...action.payload };
    default:
      return state;
  }
};

export const ExperiencesContextProvider = ({ children }) => {
  const [experienceData, dispatch] = useReducer(experiencesReducer, initExperiencesContext);

  const updateExperienceData = useCallback((newData) => {
    dispatch({ type: 'UPDATE_EXPERIENCE_DATA', payload: newData });
  }, []);

  return (
    <ExperiencesContext.Provider value={{ experienceData, updateExperienceData }}>
      {children}
    </ExperiencesContext.Provider>
  );
}

ExperiencesContextProvider.propTypes = {
  children: PropTypes.node,
}
