import { useEffect, useState, useCallback } from 'react';
import ExperiencesRepository from '../repositories/experiences.repository';

const useFetch = (action, params, body) => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const selectFetchAction = useCallback(
    () =>{
      if(action === 'searchAll') return ExperiencesRepository.search();
      if(action === 'getBySlug') return ExperiencesRepository.getBySlug(params);
      if(action === 'getAllPaginated') return ExperiencesRepository.getAllPaginated(); 
    },
    [action, params]
  )
  
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await selectFetchAction();
        setData(response);
        setLoading(false);
      } catch (error) {
        setError(error);
        setLoading(false);
      }
    };
    fetchData();
  }, [selectFetchAction]);

  return { data, loading, error };
};

export default useFetch;