import { CATEGORIES_COLUMNS, EXPERIENCES_COLUMNS, USERS_COLUMNS } from "../../schemas/tableColumns";

export const dataTable = ( data, path ) => {
  const source = {
    columns: [],
    dataSource: []
  }

  if(path === 'experiences'){
    source.columns = EXPERIENCES_COLUMNS 
    source.dataSource = data.map((exp, index)=>(
      {
        key: index,
        ...exp,
        category: exp.category.name,
      }
    ))
  }

  if(path === 'users'){
    source.columns = USERS_COLUMNS 
    source.dataSource = data.map((usr, index)=>(
      {
        key: index,
        ...usr,
      }
    ))
  }

  if(path === 'categories'){
    source.columns = CATEGORIES_COLUMNS 
    source.dataSource = data.map((cat, index)=>(
      {
        key: index,
        ...cat,
      }
    ))
  }

  return source;
}