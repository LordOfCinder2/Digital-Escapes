import { Modal, Space, message } from "antd"
import PropTypes from 'prop-types';
import { useContext, useState } from "react";
import ExperiencesRepository from "../../../../../repositories/experiences.repository";
import { AdminContext } from "../../../../../context/admin/AdminContext";

const Actions = ({ record, path }) => {

  const [open, setOpen] = useState(false);
  const [messageApi, contextHolder] = message.useMessage();
  const [confirmLoading, setConfirmLoading] = useState(false);
  const [modalText, setModalText] = useState('Deseas eliminar esta experiencia?');
  const { updateAdminData } = useContext(AdminContext)


  const handleDeleteAction = () => {
    setModalText('Eliminando experiencia, espere...');
    setConfirmLoading(true);
    const fetchData = async () => {
      try {
        const token = localStorage.getItem('token');
        await ExperiencesRepository.deleteExp(record.id, token);
        setOpen(false);
        setConfirmLoading(false);
        messageApi.open({
          type: 'success',
          content: `La experiencia ${record.name} fue eliminada correctamente`,
        });
        const response = await ExperiencesRepository.search(token);
        updateAdminData({
          experiences: response
        })
      } catch (error) {
        setOpen(false);
        setConfirmLoading(false);
        messageApi.open({
          type: 'error',
          content: 'Hubo un error, intentelo nuevamente',
        });
      }
    };
    fetchData();
  }

  // const handleUpdateAction = () => {
  //   console.log(record)
  // }

  return (
    <>
      {contextHolder}
      <Space size="middle">
        <a>Actualizar</a>
        {path === 'experiences' && <a onClick={() => setOpen(true)}>Borrar</a>}
      </Space>
      <Modal
        title="Eliminar experiencia"
        centered
        open={open}
        onOk={handleDeleteAction}
        confirmLoading={confirmLoading}
        onCancel={() => setOpen(false)}
      >
        <p>{modalText}</p>
      </Modal>
    </>
  )
}

Actions.propTypes = {
  record: PropTypes.object,
  path: PropTypes.string
}

export default Actions;