import React, {useEffect, useState} from 'react';
import { Flex, Stack, Code } from '@chakra-ui/react';
import { PostLists } from '../Components/PostLists';
import { useNavigate } from 'react-router-dom';
import axios from "axios";
import MainTemplate from "../Templates/MainTemplate";
const MainPage = () => {
  const navigate = useNavigate();

  const [hello, setHello] = useState("");

  useEffect(() => {
    axios.get('http://localhost:8080/api/post/1').then(response => setHello(response.data)).catch(error=>console.log(error));
    console.log(hello);
  },[]);



  return (
    <MainTemplate titleQuery={'옥해은'} pageTitle={'$ HaeeunOk(옥해은)의 기록 아카이브 !'}>
      <PostLists/>
    </MainTemplate>
  );
};

export default MainPage;
