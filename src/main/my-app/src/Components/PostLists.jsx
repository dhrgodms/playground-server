import { SimpleGrid } from '@chakra-ui/react';
import { MainCard } from './MainCard';
import {useEffect, useState} from "react";
import axios from "axios";
export const PostLists = () => {
  const [mainPostsdata, setMainPostsdata] = useState([]);
  useEffect(() => {
    axios.get(`http://localhost:8080/api/post/main-posts`).then(response => {
      console.log(response.data);
      setMainPostsdata(response.data);
    }).catch(error=>console.log(error));
  },[]); // slider에도 반영해주어야함
  return (
    <SimpleGrid
      spacing={4}
      templateColumns="repeat(auto-fill, minmax(200px, 1fr))"
    >
      <MainCard key={6} ListTitle={"인기글"} post={mainPostsdata[0]} />
      <MainCard key={5} ListTitle={"최신글"} post={mainPostsdata[1]} />
      <MainCard key={7} ListTitle={"생각글"}  post={mainPostsdata[2]} />
      <MainCard key={8} ListTitle={"일상만화"}  post={mainPostsdata[3]} />
      <MainCard key={9} ListTitle={"나의 보물list"}  post={mainPostsdata[4]} />
    </SimpleGrid>
  );
};
