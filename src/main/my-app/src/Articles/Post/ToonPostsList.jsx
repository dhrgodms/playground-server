import React, {useEffect, useState} from 'react';
import {Flex, SimpleGrid} from '@chakra-ui/react';
import axios from "axios";
import MainTemplate from "../../Templates/MainTemplate";
import {PostCard} from "../../Components/PostCard";

const ToonPostsList = () => {
    const [writePosts, setWritePosts] = useState([{id:1,postListTile:'꾸잉',postNo:1}]);
    useEffect(() => {
        (writePosts?.length<2)&&axios.get('http://localhost:8080/api/post/all').then(response => setWritePosts(response.data)).catch(error=>console.log(error));
        console.log(writePosts);
    },[writePosts]);
    return (
        <MainTemplate pageTitle={'고민이 담긴 글 모음'} titleQuery={'글'}>
            <Flex height={'100vh'} style={{ flexDirection: 'column' }}>
                <SimpleGrid
                    spacing={4}
                    templateColumns="repeat(auto-fill, minmax(200px, 1fr))"
                >
                    {writePosts.map(item => (
                        <PostCard key={item.id} post={item} />
                    ))}
                </SimpleGrid>
            </Flex>
        </MainTemplate>
    );
};

export default ToonPostsList;
