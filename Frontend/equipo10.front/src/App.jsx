import { Routes, Route, Navigate } from "react-router-dom";
import Auth from "./routes/Auth/component";
import Home from "./routes/Home/component";
import MainContent from "./components/layout/mainContent";
import AddExperience from "./routes/AdminPanel/Experiences/AddExperience/component";
import ExperiencesDetail from "./routes/ExperienceDetail/component";
import UserProfile from "./components/UserProfile/component";
import { ExperiencesContextProvider } from "./context/coreData/experiences.context";
import { AuthContextProvider } from "./context/auth/AuthContext";
import AuthGuard from "./components/ProtectedRoutes/component";
import AdminPanel from "./routes/AdminPanel/component";
import AddCategory from "./routes/AdminPanel/Categories/AddCategory/component";
import Experiences from "./routes/AdminPanel/Experiences/component";
import Users from "./routes/AdminPanel/Users/component";
import Categories from "./routes/AdminPanel/Categories/component";
import { AdminContextProvider } from "./context/admin/AdminContext";
import { Helmet } from 'react-helmet';
import Booking from "./routes/Booking/component";


function App() {
  return (
    <AuthContextProvider>
      <AdminContextProvider>
        <ExperiencesContextProvider>
        <Helmet>
          {/* <link rel="ico" type="image/ico" href="" /> */}
          <link rel="icon" type="image/ico" href="./src/assets/images/DE.ico" />
          <title>Digital Escapes</title>
        </Helmet>
          <MainContent>
            <main>
              <Routes>
                <Route path='/' element={<Navigate to='home' />} />
                <Route path='home' element={<Home />} />
                <Route path='login' element={<Auth />} />
                <Route path='signup' element={<Auth />} />
                <Route path='booking' element={<Booking />} />
                <Route path='detail/:experienceSlug' element={<ExperiencesDetail />} />
                <Route path='/userdetail' element={<AuthGuard><UserProfile/></AuthGuard>} />
                <Route path='adminPanel' element={<AuthGuard><AdminPanel /></AuthGuard>}>
                  <Route path='experiences' element={<AuthGuard><Experiences /></AuthGuard>} />
                  <Route path='experiences/addExperiences' element={<AuthGuard><AddExperience /></AuthGuard>} />
                  <Route path='categories' element={<AuthGuard><Categories /></AuthGuard>} />
                  <Route path='categories/addCategories' element={<AuthGuard><AddCategory/></AuthGuard>} />
                  <Route path='users' element={<AuthGuard><Users /></AuthGuard>} />
                </Route>
              </Routes>
            </main>
          </MainContent>
        </ExperiencesContextProvider>
      </AdminContextProvider>
    </AuthContextProvider>
  )
}

export default App
