import styles from './component.module.scss';

const Spinner = () => {
	return (
		<div className={styles.spinnerContainer}>
			<span className={styles.spinner}></span>
		</div>
	)
}

export default Spinner;