import { Outlet } from "react-router-dom"
import { ADMIN_ACTIONS } from "../../schemas/admin.schema"
import AdminActionCard from "./children/adminActionsMenuItems/component"
import styles from "./component.module.scss"

const Panel = () => {
  return (
    <section className={styles.panelSection}>
      <div className={styles.adminMenu}>
        <h1>Panel de administración</h1>
        {ADMIN_ACTIONS.map(({ tag, url, icon }, index) => (
          <AdminActionCard
            key={index}
            tag={tag}
            url={url}
            icon={icon}
          />
        ))}
      </div>
      <div className={styles.outletRoutes}>
        <Outlet />
      </div>
    </section>
  )
}

export default Panel