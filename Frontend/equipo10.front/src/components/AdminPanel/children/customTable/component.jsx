import { Table } from "antd";
import { useLocation } from "react-router-dom";
import { dataTable } from "../../../../utils/dataSource/dataTable";
import { last } from "ramda";
import Actions from "./actions/component";
import PropTypes from 'prop-types';
import { lastPath } from "../../../../utils/urls/lastPath";

const AdminCustomTable = ({data}) =>{
  const location = useLocation()
  const pathname = lastPath(location.pathname)
  const { columns, dataSource} = dataTable(data, pathname)
  const columnsActions = last(columns)

  columnsActions.render = (_, record) => <Actions record={record} path={pathname} />

  return(
    <Table 
      columns={columns} 
      dataSource={dataSource} 
      // onChange={}
    />
  )
}

AdminCustomTable.propTypes = {
  data: PropTypes.array
}

export default AdminCustomTable;