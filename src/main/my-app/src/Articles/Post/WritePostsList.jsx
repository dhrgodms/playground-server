import React, {useEffect, useState} from 'react';
import {Flex, SimpleGrid, Skeleton} from '@chakra-ui/react';
import axios from "axios";
import {PostCard} from "../../Components/PostCard";
import MainTemplate from "../../Templates/MainTemplate";

const WritePostsList = () => {
    const [writePosts, setWritePosts] = useState([{id:1,postListTile:'꾸잉',postNo:1}]);
    const [isLoaded, setIsLoaded] = useState(false);

    useEffect(() => {
        (writePosts?.length<2)&&axios.get('http://211.226.190.123:2023/api/post/tag/1').then(response => {
            setWritePosts(response.data);
            setIsLoaded(true);
        }).catch(error=>console.log(error));
        console.log(writePosts);
    },[writePosts]);
  return (
    <MainTemplate pageTitle={'고민이 담긴 글 모음'} titleQuery={'글'}>
      <Flex height={'100vh'} style={{ flexDirection: 'column' }}>
              <Skeleton isLoaded={isLoaded} fadeDuration={1}>
          <SimpleGrid
              spacing={4}
              templateColumns="repeat(auto-fill, minmax(200px, 1fr))"
          >
              {writePosts.map(item => (
                  (item.id>1)?(<PostCard key={item.id} post={item}/>):null
              ))}
          </SimpleGrid>
              </Skeleton>
      </Flex>
    </MainTemplate>
  );
};

export default WritePostsList;
