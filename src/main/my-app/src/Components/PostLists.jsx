import { SimpleGrid } from '@chakra-ui/react';
import { MainCard } from './MainCard';
import {useEffect, useState} from "react";
import axios from "axios";
export const PostLists = () => {
  const [recentPost, setRecentPost] = useState(0);
  useEffect(() => {
    (!recentPost)&&axios.get(`http://localhost:8080/api/post/main-posts`).then(response => {
      console.log(response.data);
      setRecentPost(response.data);
    }).catch(error=>console.log(error));
  },[recentPost]);
  return (
    <SimpleGrid
      spacing={4}
      templateColumns="repeat(auto-fill, minmax(200px, 1fr))"
    >
      <MainCard key={6} ListTitle={"인기글"} post={recentPost[0]} />
      <MainCard key={5} ListTitle={"최신글"} post={recentPost[1]} />
      <MainCard key={7} ListTitle={"생각글"}  post={recentPost[2]} />
      <MainCard key={8} ListTitle={"일상만화"}  post={recentPost[3]} />
      <MainCard key={9} ListTitle={"나의 보물list"}  post={recentPost[4]} />
    </SimpleGrid>
  );
};
