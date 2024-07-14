import { Route, Routes } from 'react-router-dom';
import './App.css';
import { ChakraProvider } from '@chakra-ui/react';
import MainPage from './Pages/MainPage';
import DefaultPost from './Articles/Post/DefaultPost';
import PlayListPost from './Articles/Post/PlayListPost';
import GuestBook from './Articles/Post/GuestBook';
import WritePostsList from './Articles/Post/WritePostsList';
import ToonPostsList from './Articles/Post/ToonPostsList';
import LoginPage from "./Pages/LoginPage";
import UploadPostPage from "./Pages/UploadPostPage";
import UpdatePostPage from "./Pages/UpdatePostPage";
import AllPostsList from "./Articles/Post/AllPostsList";
import SignUpPage from "./Pages/SignUpPage";

function App() {

  return (
      <ChakraProvider>
      <Routes>
      <Route path="/" element={<MainPage />} />
        <Route path="/post/:id" element={<DefaultPost />} />
          <Route path="/upload" element={<UploadPostPage />} />
          <Route path="/update/:id" element={<UpdatePostPage />} />

      <Route path="/writes" element={<WritePostsList />} />
        <Route path="/toons" element={<ToonPostsList />} />
          <Route path="/lists" element={<PlayListPost />} />
          <Route path="/all" element={<AllPostsList />} />
        <Route path="/guestbook" element={<GuestBook />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/signup" element={<SignUpPage />} />


      </Routes>
    </ChakraProvider>
        );
}

export default App;
