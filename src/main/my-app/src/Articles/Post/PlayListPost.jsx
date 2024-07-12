import React, {useEffect, useState} from 'react';
import {Flex, SimpleGrid, Skeleton} from '@chakra-ui/react';
import axios from "axios";
import MainTemplate from "../../Templates/MainTemplate";
import {PostCard} from "../../Components/PostCard";
import serverUrl from "../../Constants/Constants";
const PlayListBook = () => {
    const [writePosts, setWritePosts] = useState([{id:1,postListTile:'꾸잉',postNo:1}]);
    const [isLoaded, setIsLoaded] = useState(false);
    useEffect(() => {
        (writePosts?.length)&&axios.get(`${serverUrl}:8080/api/post/tag/3`).then(response => {
            setWritePosts(response.data);
            setIsLoaded(true);
        }).catch(error=>console.log(error));
        console.log(writePosts);
    },[]);
    return (
        <MainTemplate pageTitle={'이럴땐 이런 노래들어야됨'} titleQuery={'노래'}>
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

export default PlayListBook;
