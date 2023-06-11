import { Route, Routes } from 'react-router-dom';
import './App.css';
import { ChakraProvider } from '@chakra-ui/react';
import MainPage from './Pages/MainPage';
import RecentPage from './Pages/RecentPage';
import WritePost from './Articles/Post/WritePost';
import ToonPost from './Articles/Post/ToonPost';
import PlayListPost from './Articles/Post/PlayListPost';
import GuestBook from './Articles/Post/GuestBook';
import WritePostsList from './Articles/Post/WritePostsList';
import ToonPostsList from './Articles/Post/ToonPostsList';
import LoginPage from "./Pages/LoginPage";
import UploadPostPage from "./Pages/UploadPostPage";
import UpdatePostPage from "./Pages/UpdatePostPage";

function App() {

  return (
      <ChakraProvider>
      <Routes>
      <Route path="/writes" element={<WritePostsList />} />
      <Route path="/" element={<MainPage />} />
      <Route path="/recent" element={<RecentPage/>} />
        <Route path="/post/:id" element={<WritePost />} />
          <Route path="/upload" element={<UploadPostPage />} />
          <Route path="/update/:id" element={<UpdatePostPage />} />

        <Route path="/toons" element={<ToonPostsList />} />
        <Route path="/toon-post/:id" element={<ToonPost />} />
        <Route path="/playlist-post/:id" element={<PlayListPost />} />
        <Route path="/guestbook" element={<GuestBook />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/signup" element={<LoginPage />} />


      </Routes>
    </ChakraProvider>
        );
}

export default App;
